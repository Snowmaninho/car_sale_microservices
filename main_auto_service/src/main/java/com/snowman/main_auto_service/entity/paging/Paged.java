package com.snowman.main_auto_service.entity.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

// for pagination
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paged<T> {

    private Page<T> page;

    private Paging paging;

}
