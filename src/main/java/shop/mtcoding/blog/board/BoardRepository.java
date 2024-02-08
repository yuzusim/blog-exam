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

    public List<Board> findAll(int page){
        final int COUNT = 5;
        int value = page*COUNT;

        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?", Board.class);

        query.setParameter(1, value);
        query.setParameter(2, COUNT);

        List<Board> boardList = query.getResultList(); //여러건
        return boardList;
    }

    public int totalCount(){ // 모든 걸 다 조회

        Query query = em.createNativeQuery("select count(*) from board_tb");

        int count= ((Number)query.getSingleResult()).intValue();
        System.out.println("count : "+count);
        return count;
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


    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
