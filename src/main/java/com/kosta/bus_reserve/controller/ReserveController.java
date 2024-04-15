package com.kosta.bus_reserve.controller;

import com.kosta.bus_reserve.domain.SearchedDispatch;
import com.kosta.bus_reserve.domain.TerminalVO;
import com.kosta.bus_reserve.domain.TicketVO;
import com.kosta.bus_reserve.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reserve/*")
public class ReserveController {

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
                            @RequestParam String seatNo,
                            @RequestParam String people,
                            @RequestParam String departureTime,
                            @RequestParam String price,
                            HttpSession session) {
        session.setAttribute("startRegion", startRegion);
        session.setAttribute("startTerminal", startTerminal);
        session.setAttribute("endTerminal", endTerminal);
        session.setAttribute("endRegion", endRegion);
        session.setAttribute("busNo", busNo);
        session.setAttribute("seatNo", seatNo);
        session.setAttribute("people", people);
        session.setAttribute("departureTime", departureTime);
        session.setAttribute("price", price);
        return "redirect:reserve_login";
    }

    @PostMapping("/pre-used-terminal")
    @ResponseBody
    public List<SearchedDispatch> getPreUsedTerminals(@RequestParam String userId){
        //사용자 이름으로 기존에 이용했던 터미널 찾기.
        List<SearchedDispatch> recordList = reserveService.getRecordsByUserId(userId);
        return recordList;
    }
}
