package com.cholog_ai.closet_saver.clothes.controller;

import com.cholog_ai.closet_saver.clothes.Clothes;
import com.cholog_ai.closet_saver.clothes.service.SearchService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * GET /api/search/filter
     * 7가지 ENUM 필터 조건에 따라 보유 의류 목록을 조회합니다 (F-2.1, F-2.2).
     * @param userId 현재 사용자 ID (Mock Data 기반 검증에서는 1로 고정)
     */
    @GetMapping("/filter")
    public String searchSimilarItems(
            @RequestParam("userId") Long userId,
            // (나머지 7가지 String 파라미터는 SearchService로 그대로 전달)
            @RequestParam(value = "itemType", required = false) String itemTypeStr,
            @RequestParam(value = "colorGroup", required = false) String colorGroupStr,
            @RequestParam(value = "neckType", required = false) String neckTypeStr,
            @RequestParam(value = "fitType", required = false) String fitTypeStr,
            @RequestParam(value = "thickness", required = false) String thicknessStr,
            @RequestParam(value = "sleeveType", required = false) String sleeveTypeStr,
            @RequestParam(value = "pattern", required = false) String patternStr,
            Model model // Thymeleaf에 데이터를 전달하기 위한 Model 객체
    ) {
        // 1. Service 호출 및 유사 의류 목록 조회
        List<Clothes> similarClothes = searchService.searchSimilarItems(
                userId, itemTypeStr, colorGroupStr, neckTypeStr, fitTypeStr, thicknessStr, sleeveTypeStr, patternStr
        );

        // 2. 결과를 Thymeleaf 템플릿으로 전달 (모델에 추가)
        model.addAttribute("similarItems", similarClothes);
        model.addAttribute("foundCount", similarClothes.size());

        // 3. 템플릿 파일명 반환 (src/main/resources/templates/search_results.html 파일을 찾음)
        return "search_results";
    }

}
