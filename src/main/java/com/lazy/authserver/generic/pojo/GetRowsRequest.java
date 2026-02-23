package com.lazy.authserver.generic.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
@Valid
public class GetRowsRequest implements Serializable {

    @JsonProperty("pq_curpage")
    @Min(value = 1, message = "Page cannot be less than ")
    private int pq_curpage = 1;

    @JsonProperty("pq_rpp")
    @Min(value = 1, message = "Request Per Page cannot be less than ")
    private int pq_rpp = 10;

    private String pq_filter;
    @JsonProperty("pq_sort")
    private String pq_sort;

    private String filters;

    @JsonProperty("sort_model_sting")
    private String sort_model_sting;

    public int getPq_curpage() {
        if (pq_curpage == 0)
            return pq_curpage;
        return pq_curpage - 1;
    }
}
