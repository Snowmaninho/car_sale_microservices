package com.snowman.main_auto_service.entity.paging;

import lombok.*;

// for pagination
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {

    private PageItemType pageItemType;

    private int index;

    private boolean active;

}
