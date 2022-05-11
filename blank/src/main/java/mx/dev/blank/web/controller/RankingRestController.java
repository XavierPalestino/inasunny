package mx.dev.blank.web.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.dev.blank.service.RankingService;
import mx.dev.blank.web.request.RankingRequest;
import mx.dev.blank.web.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingRestController {

  private final RankingService rankingService;

  @PostMapping
  public ResponseEntity<BaseResponse> createRanking(
      @Valid @RequestBody final RankingRequest rankingRequest, final BindingResult errors) {

    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(BaseResponse.fail(errors.getAllErrors()));
    }

    rankingService.createRanking(rankingRequest);

    return ResponseEntity.ok(BaseResponse.success("Ranking created successfully"));
  }
}
