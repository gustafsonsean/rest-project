package com.eco.portfoliomanagement.model.web;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pageNumber",
        "pageSize",
        "numberOfPages",
        "totalNumberOfElements",
        "page"
})
public class AdvisorModelWebList {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("pageNumber")
    private Integer pageNumber;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("pageSize")
    private Integer pageSize;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("numberOfPages")
    private Integer numberOfPages;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("totalNumberOfElements")
    private Integer totalNumberOfElements;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("page")
    private List<PortfolioModelWeb> page = null;

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("pageNumber")
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("pageNumber")
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("numberOfPages")
    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("numberOfPages")
    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("totalNumberOfElements")
    public Integer getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("totalNumberOfElements")
    public void setTotalNumberOfElements(Integer totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("page")
    public List<PortfolioModelWeb> getPage() {
        return page;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("page")
    public void setPage(List<PortfolioModelWeb> page) {
        this.page = page;
    }

}