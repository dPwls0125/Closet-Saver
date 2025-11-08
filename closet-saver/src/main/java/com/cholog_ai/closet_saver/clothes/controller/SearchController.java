package com.cholog_ai.closet_saver.clothes.controller;

import com.cholog_ai.closet_saver.clothes.Clothes;
import com.cholog_ai.closet_saver.clothes.dto.SimilarItemDTO;
import com.cholog_ai.closet_saver.clothes.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * GET /search/filter
     * 7가지 ENUM 필터 조건에 따라 유사 의류를 조회하고 Thymeleaf 템플릿으로 렌더링합니다.
     */
    @GetMapping("/filter")
    public String searchSimilarItems(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "itemType", required = false) String itemTypeStr,
            @RequestParam(value = "colorGroup", required = false) String colorGroupStr,
            @RequestParam(value = "neckType", required = false) String neckTypeStr,
            @RequestParam(value = "fitType", required = false) String fitTypeStr,
            @RequestParam(value = "thickness", required = false) String thicknessStr,
            @RequestParam(value = "sleeveType", required = false) String sleeveTypeStr,
            @RequestParam(value = "pattern", required = false) String patternStr,
            Model model // Thymeleaf 모델
    ) {
        // 1. Service 호출: Entity 목록 획득
        List<Clothes> similarClothes = searchService.searchSimilarItems(
                userId, itemTypeStr, colorGroupStr, neckTypeStr, fitTypeStr, thicknessStr, sleeveTypeStr, patternStr
        );

        // 2. Entity 목록을 Record DTO 목록으로 변환 (핵심)
        List<SimilarItemDTO> itemDTOs = similarClothes.stream()
                .map(this::mapToSimilarItemDTO)
                .collect(Collectors.toList());

        // 3. Thymeleaf 템플릿에 데이터 전달
        model.addAttribute("similarItems", itemDTOs);
        model.addAttribute("foundCount", itemDTOs.size());

        return "search_results";
    }

    /**
     * Clothes Entity를 SimilarItemDTO Record로 매핑하는 헬퍼 메서드
     * (DTO를 사용함으로써 DB 세션 및 불필요한 데이터 노출 위험 제거)
     */
    private SimilarItemDTO mapToSimilarItemDTO(Clothes clothes) {
        // LocalDate, String 등의 타입을 안전하게 매핑합니다.
        return new SimilarItemDTO(
                clothes.getClothId(),
                clothes.getImageUrl(),
                clothes.getName(),
                clothes.getPurchasedDate(), // LocalDate 타입
                clothes.getColorGroup().name(), // ENUM을 String으로 변환
                clothes.getThickness().name()
        );
    }

}
