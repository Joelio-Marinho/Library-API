package com.joelio.libraryapi.DTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    @NotEmpty
    @NotNull
    private String title;
    @NotEmpty
    @NotNull
    private String author;
    @NotEmpty
    @NotNull
    private String isbn;

}
