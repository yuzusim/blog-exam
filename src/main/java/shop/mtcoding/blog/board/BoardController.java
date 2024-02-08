package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private HttpSession session;
    private final BoardRepository boardRepository; // DI
//, @RequestParam(defaultValue = "0") int page
    // http://localhost:8080?page=0
    @GetMapping({"/", "/board"})
    public String index(HttpServletRequest request) {
        //System.out.println("페이지: " +page);
        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("boardList", boardList); // ("key", value)
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {


        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, HttpServletRequest request) {




        Board board = boardRepository.findById(id);
        if (board.getUsername() == null) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "제목과 내용은 20자를 넘어갈 수 없습니다");
            return "error/40x";
        }

        request.setAttribute("board", board);

        return "board/updateForm";
    }


    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO, HttpServletRequest request){
        // 바디 데이터 확인 및 유효성 검사
        System.out.println(requestDTO);

        if(requestDTO.getTitle().length() > 20 || requestDTO.getContent().length() > 20){
            request.setAttribute("status", 400);
            request.setAttribute("msg", "제목과 내용은 20자를 넘어갈 수 없습니다");
            return "error/40x"; // BadRequest
        }

        boardRepository.save(requestDTO);

        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id, BoardRequest.UpdateDTO requestDTO, HttpServletRequest request){
        // 바디 데이터 확인 및 유효성 검사
        System.out.println(requestDTO);

        if(requestDTO.getTitle().length() > 20 || requestDTO.getContent().length() > 20){
            request.setAttribute("status", 400);
            request.setAttribute("msg", "제목과 내용은 20자를 넘어갈 수 없습니다");
            return "error/40x"; // BadRequest
        }

        boardRepository.update(requestDTO, id);

        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id, HttpServletRequest request){
        Board board = boardRepository.findById(id);

        boardRepository.deleteById(id);
        return "redirect:/";
    }
}
