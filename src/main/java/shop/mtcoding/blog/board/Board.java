package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="board_tb")
@Data
@Entity
public class Board {
    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 전략
    private int id;

    @Column(length = 20)
    private String title;
    private String content;
    private String username;


}
