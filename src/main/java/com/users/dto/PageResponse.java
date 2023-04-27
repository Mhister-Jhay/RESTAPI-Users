package com.users.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponse {
    private List<?> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private boolean last;
    private int totalPages;
}
