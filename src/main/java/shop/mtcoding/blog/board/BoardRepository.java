package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    public List<Board> findAll(){
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
        return query.getResultList();
    }

    public Board findById(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        query.setParameter(1, id);

        Board board = (Board) query.getSingleResult();
        return board;
    }

    @Transactional
    public void save(BoardRequest.SaveDTO requestDTO) {
        Query query = em.createNativeQuery("insert into board_tb(title, content, username) values(?,?,?);");
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, requestDTO.getUsername());

        query.executeUpdate();

    }

    @Transactional
    public void update(BoardRequest.UpdateDTO requestDTO, int id) {
        //  Query query = em.createNativeQuery("update board_tb set title =?, content =?, username = ?, where id =?, now())");
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ?, username = ? where id = ?");
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, requestDTO.getUsername());
        query.setParameter(4, id);

        query.executeUpdate();
    }


}
