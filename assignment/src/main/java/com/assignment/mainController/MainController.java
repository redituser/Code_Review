package com.assignment.mainController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.assignment.AssignmentApplication;
import com.assignment.board.service.BoardService;
import com.assignment.board.vo.BoardSummaryDto;
import com.assignment.board.vo.BoardVO;
import com.assignment.invite.service.InviteService;
import com.assignment.invite.vo.InviteDTO;
import com.assignment.like.service.LikeService;
import com.assignment.member.service.MemberService;
import com.assignment.member.vo.MemberVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	private final AssignmentApplication assignmentApplication;

	MainController(AssignmentApplication assignmentApplication) {
		this.assignmentApplication = assignmentApplication;
	}

	@Autowired
	InviteService inviteService;

	@Autowired
	BoardService boardService;

	@Autowired
	LikeService likeService;

	@Autowired
	MemberService memberService;

	@GetMapping("/")
	public String mainPage(HttpSession session, Model model) {
		String loginId = (String) session.getAttribute("loginId");
		if (loginId != null) {
			model.addAttribute("loginId", loginId);
			model.addAttribute("isLogin", true);

			try {
				MemberVO member = memberService.getMemberById(loginId);
				if (member != null && member.getType() == 'A') {
					model.addAttribute("isAdmin", true);
				}
			} catch (Exception e) {
				// 사용자를 찾을 수 없는 경우 등 예외 처리
				model.addAttribute("isAdmin", false);
			}

			// 내가 작성한 게시글
			List<BoardVO> myPostsVO = boardService.getPostsByWriterId(loginId);
			model.addAttribute("myPosts", convertToDto(myPostsVO));

			// 좋아요한 게시글
			List<BoardVO> likedPostsVO = likeService.getLikedBoardsByMember(loginId);
			model.addAttribute("likedPosts", convertToDto(likedPostsVO));

			// 최신 게시글
			List<BoardVO> recentPostsVO = boardService.getRecentPosts();
			model.addAttribute("recentPosts", convertToDto(recentPostsVO));

			// 인기 게시글
			List<BoardVO> popularPostsVO = boardService.getTop5PopularPosts();
			model.addAttribute("popularPosts", convertToDto(popularPostsVO));

			// 초대 개수
			List<InviteDTO> myInvites = inviteService.getMyUnifiedInvites(loginId);
			model.addAttribute("inviteCount", myInvites.size());

		} else {
			model.addAttribute("isLogin", false);
		}

		return "main/main";
	}

	@GetMapping("/user/login")
	public String loginPage() {
		System.out.println("로그인 호출");
		return "user/login";
	}

	private List<BoardSummaryDto> convertToDto(List<BoardVO> boards) {
		if (boards == null) {
			return List.of(); // boards가 null일 경우 빈 리스트 반환
		}
		return boards.stream().map(board -> {
			// 각 게시글의 좋아요 수를 조회
			long likeCount = likeService.getLikeCount(board.getBoardNo());
			// DTO로 변환
			return new BoardSummaryDto(board, likeCount);
		}).collect(Collectors.toList());
	}

}
