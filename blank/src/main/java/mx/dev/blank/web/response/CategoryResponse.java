package mx.dev.blank.web.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.model.CategoryDTO;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CategoryResponse {

    private final List<CategoryDTO> categories;
}
