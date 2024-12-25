package worldwide.clm.clmwebsite.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.MemberUpdateRequest;
import worldwide.clm.clmwebsite.dto.request.MinisterRegistrationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.memberServices.MemberService;
import worldwide.clm.clmwebsite.services.membersData.MembersDataService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.List;

@RestController
@RequestMapping("/clmWebsite/api/v1/members-data")
@RequiredArgsConstructor
public class MembersDataController {
    private final MembersDataService memberService;

    @PatchMapping("list")
    public ResponseEntity<ApiResponse> updateMembersDataList(@RequestBody List<MemberUpdateRequest> memberUpdateRequests) throws UserNotFoundException {
        memberService.updateMembersDataList(memberUpdateRequests);
        ApiResponse apiResponse = ResponseUtils.getUpdatedMessage();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PatchMapping("")
    public ResponseEntity<ApiResponse> updateMembersData(@RequestBody MemberUpdateRequest memberUpdateRequest) throws UserNotFoundException {
        memberService.updateMemberData(memberUpdateRequest);
        ApiResponse apiResponse = ResponseUtils.getUpdatedMessage();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> findAll() {
        var result = memberService.findAll();
        ApiResponse apiResponse = ResponseUtils.getRetrievalMessage(result);
        apiResponse.setSize(result.size());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchByParam(
            @RequestParam(name = "searchParam") String searchParam
    ) throws UserNotFoundException {
        var result = memberService.search(searchParam);
        ApiResponse apiResponse = ResponseUtils.getRetrievalMessage(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @GetMapping("/members-specific-detail-list")
    public ResponseEntity<ApiResponse> retrieveAllMembersPhoneNumber(
            @RequestParam(name = "searchParam", defaultValue = "phoneNumber") String searchParam,
            @RequestParam(name = "format", defaultValue = "json") String format
    ) throws UserNotFoundException {
        var result = memberService.retrieveAllMembersPhoneNumber(searchParam, format);
        ApiResponse apiResponse = ResponseUtils.getRetrievalMessage(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
