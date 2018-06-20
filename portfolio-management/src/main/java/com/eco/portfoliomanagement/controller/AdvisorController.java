package com.eco.portfoliomanagement.controller;

import com.eco.portfoliomanagement.model.db.Advisor;
import com.eco.portfoliomanagement.model.db.PortfolioModelAssetAllocation;
import com.eco.portfoliomanagement.model.db.PortfolioModel;
import com.eco.portfoliomanagement.model.web.AdvisorModelWebList;
import com.eco.portfoliomanagement.model.web.AssetAllocation;
import com.eco.portfoliomanagement.model.web.PortfolioModelWeb;
import com.eco.portfoliomanagement.model.web.CustomErrorType;
import com.eco.portfoliomanagement.service.AdvisorService;
import com.eco.portfoliomanagement.service.PortfolioModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class AdvisorController {
    private static final Logger log = LoggerFactory.getLogger(AdvisorController.class);

    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    AdvisorService advisorService;

    @Autowired
    PortfolioModelService portfolioModelService;

    @RequestMapping(value = "/advisor/{advisorId}/model", method = RequestMethod.GET)
    public ResponseEntity<?> getAllModelsForAdvisorId( @PathVariable("advisorId") String advisorId , @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        log.info("Advisor PortfolioModel called for " + advisorId);
        Advisor advisor = advisorService.findByAdvisorId(advisorId);
        if (pageNumber == null)
        {
            pageNumber = 0;
        }
        if (pageSize == null)
        {
            pageSize = 10;
        }
        if (advisor == null) {
            log.info("Advisor Not Found");
            return new ResponseEntity(new CustomErrorType("advisor.not.found"), HttpStatus.NOT_FOUND);
        }
        //

        Page<PortfolioModel> portfolioModelPaged = portfolioModelService.findByAdvisorPaged(advisor, pageNumber, pageSize);

        if (portfolioModelPaged == null)
        {
            log.debug("Portfolio Model Service is null " );
            return new ResponseEntity(new CustomErrorType("unable.to.complete.requested.action"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<PortfolioModel> portfolioModelList = portfolioModelPaged.getContent();
        AdvisorModelWebList advisorModelWebList = new AdvisorModelWebList();

        List<PortfolioModelWeb> portfolioModelWebList = new ArrayList<>();
        for (PortfolioModel portfolioModel : portfolioModelList)
        {
            PortfolioModelWeb portfolioModelWeb = new PortfolioModelWeb();
            portfolioModelWeb.setAdvisorId(advisor.getAdvisorId());
            portfolioModelWeb.setGuid(portfolioModel.getGuid());
            portfolioModelWeb.setCashHoldingPercentage(portfolioModel.getCashHoldingPercentage());
            portfolioModelWeb.setCreatedOn(portfolioModel.getCreatedOn());
            portfolioModelWeb.setDriftPercentage(portfolioModel.getDriftPercentage());
            portfolioModelWeb.setName(portfolioModel.getName());
            portfolioModelWeb.setDescription(portfolioModel.getDescription());
            if (portfolioModel.getModelType().equals(PortfolioModel.ModelType.QUALIFIED))
            {
                portfolioModelWeb.setModelType(PortfolioModelWeb.ModelType.QUALIFIED);
            } else if (portfolioModel.getModelType().equals(PortfolioModel.ModelType.TAXABLE))
            {
                portfolioModelWeb.setModelType(PortfolioModelWeb.ModelType.TAXABLE);
            }

            if (portfolioModel.getRebalanceFrequency().equals(PortfolioModel.RebalanceFrequency.ANNUAL)) {
                portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.ANNUAL);
            } else if (portfolioModel.getRebalanceFrequency().equals(PortfolioModel.RebalanceFrequency.MONTHLY)) {
                portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.MONTHLY);
            } else if (portfolioModel.getRebalanceFrequency().equals(PortfolioModel.RebalanceFrequency.SEMI_ANNUAL)) {
                portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.SEMI_ANNUAL);
            } else if (portfolioModel.getRebalanceFrequency().equals(PortfolioModel.RebalanceFrequency.QUARTERLY)) {
                portfolioModelWeb.setRebalanceFrequency(PortfolioModelWeb.RebalanceFrequency.QUARTERLY);
            }
            List<AssetAllocation> assetAllocationList = new ArrayList<>();
            for (PortfolioModelAssetAllocation portfolioModelAssetAllocation : portfolioModel.getPortfolioModelAssetAllocations())
            {
                AssetAllocation allocation = new AssetAllocation();
                allocation.setPercentage(portfolioModelAssetAllocation.getPercentage());
                allocation.setSymbol(portfolioModelAssetAllocation.getSymbol());
                assetAllocationList.add(allocation);
            }
            portfolioModelWeb.setAssetAllocations(assetAllocationList);
            portfolioModelWebList.add(portfolioModelWeb);
        }
        advisorModelWebList.setPage(portfolioModelWebList);
        advisorModelWebList.setPageNumber(portfolioModelPaged.getNumber());
        advisorModelWebList.setPageSize(portfolioModelPaged.getSize());
        advisorModelWebList.setTotalNumberOfElements(Math.toIntExact(portfolioModelPaged.getTotalElements()));
        advisorModelWebList.setNumberOfPages(portfolioModelPaged.getTotalPages());

        return new ResponseEntity(advisorModelWebList, HttpStatus.OK);

    }

    @RequestMapping(value = "/advisor/{advisorId}/model", method = RequestMethod.PUT)
    public ResponseEntity<?> updateModelForAdvisorId( @PathVariable("advisorId") String advisorId, @RequestBody PortfolioModelWeb portfolioModelWeb ) {
        log.info("Update PortfolioModel called for " + advisorId);
        Advisor advisor = advisorService.findByAdvisorId(advisorId);
        if (advisor == null) {
            log.debug("Advisor Not Found");
            return new ResponseEntity(new CustomErrorType("advisor.not.found"), HttpStatus.NOT_FOUND);
        }
        if (portfolioModelWeb == null) {
            log.debug("No request body found");
            return new ResponseEntity(new CustomErrorType("advisor.data.missing"), HttpStatus.BAD_REQUEST);
        }
        Double total = 0.0;
        for (AssetAllocation assetAllocation : portfolioModelWeb.getAssetAllocations())
        {
            //Force rounding of the percentages.
            assetAllocation.setPercentage(Math.round(assetAllocation.getPercentage() * 10000.0) / 10000.0);
            total += assetAllocation.getPercentage();
        }
        // must add up to 100% to nearest ten thousandth of a percent.
        if (Math.round(total * 10000.0) / 10000.0 != 100.0000)
            return new ResponseEntity(new CustomErrorType("allocation.percentage.total.invalid"), HttpStatus.BAD_REQUEST);

        List<PortfolioModel> portfolioModels = portfolioModelService.findByAdvisor(advisor);
        PortfolioModel portfolioModel = new PortfolioModel();
        Boolean update = false;
        //Checking if the named portfolio model already exists by Advisor only--we can still have duplicate names across all advisors.
        if (portfolioModels != null) {
            for (PortfolioModel pm : portfolioModels) {
                if (pm.getName().equals(portfolioModelWeb.getName()))
                {
                    portfolioModel = pm;
                    update = true;
                    break;
                }
            }
        }
        portfolioModel.setName(portfolioModelWeb.getName());
        portfolioModel.setCashHoldingPercentage(portfolioModelWeb.getCashHoldingPercentage());
        portfolioModel.setDescription(portfolioModelWeb.getDescription());
        portfolioModel.setDriftPercentage(portfolioModelWeb.getDriftPercentage());
        // Assuming the date created is the user's choice.

        try {
            sdfDate.setLenient(false);
            sdfDate.parse(portfolioModelWeb.getCreatedOn());
        } catch (ParseException e) {
            return new ResponseEntity(new CustomErrorType("date.created.on.invalid"), HttpStatus.BAD_REQUEST);
        }
        portfolioModel.setCreatedOn(portfolioModelWeb.getCreatedOn());
        if (portfolioModelWeb.getModelType().equals(PortfolioModelWeb.ModelType.QUALIFIED))
        {
            portfolioModel.setModelType(PortfolioModel.ModelType.QUALIFIED);
        } else if (portfolioModelWeb.getModelType().equals(PortfolioModelWeb.ModelType.TAXABLE))
        {
            portfolioModel.setModelType(PortfolioModel.ModelType.TAXABLE);
        } else {
            // Will not happen unless the web model has new enums declared. JSON parser will catch before hitting this point
            return new ResponseEntity(new CustomErrorType("portfolio.model.invalid"), HttpStatus.BAD_REQUEST);
        }

        if (portfolioModelWeb.getRebalanceFrequency().equals(PortfolioModelWeb.RebalanceFrequency.ANNUAL)) {
            portfolioModel.setRebalanceFrequency(PortfolioModel.RebalanceFrequency.ANNUAL);
        } else if (portfolioModelWeb.getRebalanceFrequency().equals(PortfolioModelWeb.RebalanceFrequency.MONTHLY)) {
            portfolioModel.setRebalanceFrequency(PortfolioModel.RebalanceFrequency.MONTHLY);
        } else if (portfolioModelWeb.getRebalanceFrequency().equals(PortfolioModelWeb.RebalanceFrequency.SEMI_ANNUAL)) {
            portfolioModel.setRebalanceFrequency(PortfolioModel.RebalanceFrequency.SEMI_ANNUAL);
        } else if (portfolioModelWeb.getRebalanceFrequency().equals(PortfolioModelWeb.RebalanceFrequency.QUARTERLY)) {
        portfolioModel.setRebalanceFrequency(PortfolioModel.RebalanceFrequency.QUARTERLY);
        } else {
            // Will not happen unless the web model has new enums declared. JSON parser will catch before hitting this point
            return new ResponseEntity(new CustomErrorType("rebalance.frequency.invalid"), HttpStatus.BAD_REQUEST);
        }
        List<PortfolioModelAssetAllocation> portfolioModelAssetAllocations;
        if (!update) {
            portfolioModelAssetAllocations = new ArrayList<>();
        } else
        {
            portfolioModelAssetAllocations = portfolioModel.getPortfolioModelAssetAllocations();
            portfolioModelAssetAllocations.clear();
        }
        for (AssetAllocation asset: portfolioModelWeb.getAssetAllocations())
        {
            PortfolioModelAssetAllocation portfolioModelAssetAllocation = new PortfolioModelAssetAllocation();
            portfolioModelAssetAllocation.setPercentage(asset.getPercentage());
            portfolioModelAssetAllocation.setSymbol(asset.getSymbol());
            portfolioModelAssetAllocations.add(portfolioModelAssetAllocation);
        }
        portfolioModel.setPortfolioModelAssetAllocations(portfolioModelAssetAllocations);

        portfolioModel.setDateLastModified(new Date());
        if (!update) {
            portfolioModel.setDateAdded(new Date());
            portfolioModel.setGuid(java.util.UUID.randomUUID().toString());
            portfolioModel.setAdvisor(advisor);
        }
        portfolioModelService.savePortfolioModel(portfolioModel);

        portfolioModelWeb.setGuid(portfolioModel.getGuid());
        portfolioModelWeb.setAdvisorId(advisor.getAdvisorId());
        return new ResponseEntity(portfolioModelWeb, HttpStatus.OK);


    }

    // Method to programmatically create a new advisor. No post body at this time since advisor has no properties.
    @RequestMapping(value = "/advisor", method = RequestMethod.POST)
    public ResponseEntity<?> createAdvisor(UriComponentsBuilder ucBuilder ) {
        log.debug("Creating new advisor");
        Advisor advisor = new Advisor();
        advisor.setAdvisorId(java.util.UUID.randomUUID().toString());
        advisorService.saveAdvisor(advisor);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/v1/advisor/{advisorId}").buildAndExpand(advisor.getAdvisorId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
