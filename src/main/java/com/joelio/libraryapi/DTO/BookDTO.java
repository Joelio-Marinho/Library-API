package com.joelio.libraryapi.DTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;

    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String author;
    @NotNull
    @NotEmpty
    private String isbn;

}
