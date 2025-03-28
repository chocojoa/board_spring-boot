package com.lollipop.board.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Alias("menuApiDTO")
public class MenuApiDTO {

    private Long menuId;

    private String apiUrl;

}
