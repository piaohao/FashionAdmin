package org.piaohao.fashionadmin.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.piaohao.fashionadmin.db.system.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableData<T> {
    private List<T> list;
    private Pagination pagination;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Pagination {
        private Long total;
        private Long pageSize;
        private Long current;
    }

    @SuppressWarnings("all")
    public static <T> TableData<T> fromPage(IPage<T> page) {
        return TableData.<T>builder()
                .list(page.getRecords())
                .pagination(TableData.Pagination.builder()
                        .current(page.getCurrent())
                        .pageSize(page.getSize())
                        .total(page.getTotal())
                        .build())
                .build();
    }
}
