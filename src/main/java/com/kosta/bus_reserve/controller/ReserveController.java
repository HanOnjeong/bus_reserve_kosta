package com.kosta.bus_reserve.controller;

import com.kosta.bus_reserve.config.auth.PrincipalDetail;
import com.kosta.bus_reserve.domain.*;
import com.kosta.bus_reserve.service.MemberService;
import com.kosta.bus_reserve.service.ReserveService;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reserve/*")
public class ReserveController {

    //로그인한 사용자의 id 가져오는 메서드 생성
    private String getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetail userDetails = (PrincipalDetail) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    @Autowired
    private ReserveService reserveService;


    @GetMapping("test")
    public String test(){
        return "test";
    }

    @GetMapping("reserve_main")
    public String getTerminalList(Model model) {
        //System.out.println("service: " + reserveService);
        List<TerminalVO> terminals = reserveService.getTerminalList(); // Service에서 List<TerminalVO>를 가져옴
        //System.out.println(terminals); //test
        model.addAttribute("terminals", terminals);// View에 List<TerminalVO>를 전달
        return "reserve/reserve_main"; //reserve_main.jsp
    }
    @GetMapping("/start")
    @ResponseBody
    public List<SearchedDispatch> getCandidates(@RequestParam String terminalName) {
        List<SearchedDispatch> candidates = reserveService.getEndCandidate(terminalName);
        return candidates ;
    }

    @PostMapping("/dispatch")
    @ResponseBody
    public List<SearchedDispatch> getDispatches(@RequestBody SearchedDispatch searchedDispatch){
        List<SearchedDispatch> dispatches = reserveService.getDispatchListBySearch(searchedDispatch);
        return dispatches;
    }

    @GetMapping("/seat-number")
    @ResponseBody
    public List<TicketVO> tickets(@RequestParam int dispatchNo){
        List<TicketVO> tickets = reserveService.getTicketsByDispatchNo(dispatchNo);
        return tickets;
    }

    @PostMapping("/check-info") //예매정보를 결제페이지로 넘김.
    public String checkInfo(@RequestParam String startRegion,
                            @RequestParam String startTerminal,
                            @RequestParam String endTerminal,
                            @RequestParam String endRegion,
                            @RequestParam String busNo,
                            @RequestParam String[] seatNo,  //배열로 받음.
                            @RequestParam String people,
                            @RequestParam String departureTime,
                            @RequestParam String price,
                            @RequestParam String dispatchNo,
                            @RequestParam String userId,
                            HttpSession session) {

        System.out.println("ReserveController-현재 사용자 아이디: "+userId);
        session.setAttribute("startRegion", startRegion);
        session.setAttribute("startTerminal", startTerminal);
        session.setAttribute("endTerminal", endTerminal);
        session.setAttribute("endRegion", endRegion);
        session.setAttribute("busNo", busNo);
        session.setAttribute("seatNo", seatNo);
        session.setAttribute("people", people);
        session.setAttribute("departureTime", departureTime);
        session.setAttribute("dispatchNo", dispatchNo);
        session.setAttribute("price", price);

        if(userId.equals("anonymousUser")){ //비회원 일때
            return "redirect:reserve_login";
        }

        //회원일때
        return ("redirect:reserve_pay/"+userId);

    }

    @PostMapping("/pre-used-terminal")
    @ResponseBody
    public List<SearchedDispatch> getPreUsedTerminals(@RequestParam String userId){
        //사용자 이름으로 기존에 이용했던 터미널 찾기.
        List<SearchedDispatch> recordList = reserveService.getRecordsByUserId(userId);
        return recordList;
    }

    /*승차권 예매 조회(회원)*/
    @GetMapping("/reserve_list")
    public List<ReserveList> reserveListView(Model model) {
        String userId = getCurrentUserId();
        List<ReserveList> reserveList = reserveService.getMemberReserveList(userId);
        System.out.println("test: " + reserveList);
        model.addAttribute("reserveList", reserveList);
        return reserveList;
    }

}
