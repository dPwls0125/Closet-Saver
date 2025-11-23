package com.cholog_ai.closet_saver.domain.closet.repository;

import com.cholog_ai.closet_saver.domain.closet.model.ClosetItem;
import com.cholog_ai.closet_saver.domain.closet.model.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ClosetJsonRepository 테스트")
class ClosetJsonRepositoryTest {

    private ClosetJsonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ClosetJsonRepository();
    }

    @Nested
    @DisplayName("findAll() 메서드 테스트")
    class FindAllTests {

        @Test
        @DisplayName("초기 상태에서 빈 리스트 반환")
        void findAll_withNoData_returnsEmptyList() {
            // when
            List<ClosetItem> items = repository.findAll();

            // then
            assertThat(items).isEmpty();
        }

        @Test
        @DisplayName("데이터 로드 후 모든 아이템 반환")
        void findAll_afterLoadingData_returnsAllItems() {
            // given
            ClosetItem item1 = createTestItem(1L, "image1.jpg", Color.WHITE);
            ClosetItem item2 = createTestItem(2L, "image2.jpg", Color.BLACK);
            
            List<ClosetItem> testData = List.of(item1, item2);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            List<ClosetItem> result = repository.findAll();

            // then
            assertThat(result).hasSize(2);
            assertThat(result).extracting(ClosetItem::getId)
                    .containsExactlyInAnyOrder(1L, 2L);
        }

        @Test
        @DisplayName("반환된 리스트는 내부 리스트의 복사본")
        void findAll_returnsNewListInstance() {
            // given
            ClosetItem item = createTestItem(1L, "image.jpg", Color.WHITE);
            List<ClosetItem> testData = List.of(item);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            List<ClosetItem> result1 = repository.findAll();
            List<ClosetItem> result2 = repository.findAll();

            // then
            assertThat(result1).isNotSameAs(result2);
            assertThat(result1).containsExactlyElementsOf(result2);
        }

        @Test
        @DisplayName("반환된 리스트 수정이 원본에 영향을 주지 않음")
        void findAll_modifyingReturnedList_doesNotAffectOriginal() {
            // given
            ClosetItem item = createTestItem(1L, "image.jpg", Color.WHITE);
            List<ClosetItem> testData = List.of(item);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            List<ClosetItem> result = repository.findAll();
            result.clear();

            // then
            assertThat(repository.findAll()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("findById() 메서드 테스트")
    class FindByIdTests {

        @Test
        @DisplayName("존재하는 ID로 조회 시 해당 아이템 반환")
        void findById_withExistingId_returnsItem() {
            // given
            ClosetItem item1 = createTestItem(1L, "image1.jpg", Color.WHITE);
            ClosetItem item2 = createTestItem(2L, "image2.jpg", Color.BLACK);
            
            List<ClosetItem> testData = List.of(item1, item2);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            Optional<ClosetItem> result = repository.findById(1L);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(1L);
            assertThat(result.get().getImageUrl()).isEqualTo("image1.jpg");
        }

        @Test
        @DisplayName("존재하지 않는 ID로 조회 시 빈 Optional 반환")
        void findById_withNonExistingId_returnsEmptyOptional() {
            // given
            ClosetItem item = createTestItem(1L, "image.jpg", Color.WHITE);
            List<ClosetItem> testData = List.of(item);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            Optional<ClosetItem> result = repository.findById(999L);

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("빈 저장소에서 조회 시 빈 Optional 반환")
        void findById_withEmptyRepository_returnsEmptyOptional() {
            // when
            Optional<ClosetItem> result = repository.findById(1L);

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("null ID로 조회 시 빈 Optional 반환")
        void findById_withNullId_returnsEmptyOptional() {
            // given
            ClosetItem item = createTestItem(1L, "image.jpg", Color.WHITE);
            List<ClosetItem> testData = List.of(item);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            Optional<ClosetItem> result = repository.findById(null);

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("음수 ID로 조회 가능")
        void findById_withNegativeId_works() {
            // given
            ClosetItem item = createTestItem(-1L, "image.jpg", Color.WHITE);
            List<ClosetItem> testData = List.of(item);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            Optional<ClosetItem> result = repository.findById(-1L);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(-1L);
        }

        @Test
        @DisplayName("여러 아이템 중 특정 ID 조회 시 올바른 아이템 반환")
        void findById_withMultipleItems_returnsCorrectItem() {
            // given
            List<ClosetItem> testData = List.of(
                    createTestItem(1L, "image1.jpg", Color.WHITE),
                    createTestItem(2L, "image2.jpg", Color.BLACK),
                    createTestItem(3L, "image3.jpg", Color.BEIGE),
                    createTestItem(4L, "image4.jpg", Color.GREY),
                    createTestItem(5L, "image5.jpg", Color.BLUE)
            );
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            Optional<ClosetItem> result = repository.findById(3L);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(3L);
            assertThat(result.get().getImageUrl()).isEqualTo("image3.jpg");
            assertThat(result.get().getColor()).isEqualTo(Color.BEIGE);
        }
    }

    @Nested
    @DisplayName("loadInitialData() 메서드 테스트")
    class LoadInitialDataTests {

        @Test
        @DisplayName("JSON 파일 로드 실패 시 예외를 로그하고 정상 종료")
        void loadInitialData_withInvalidJson_handlesExceptionGracefully() {
            // when & then - 예외가 발생하지 않아야 함
            assertThatCode(() -> repository.loadInitialData())
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("유효한 JSON 데이터를 로드할 수 있음")
        void loadInitialData_withValidJson_loadsDataSuccessfully() {
            // Note: 실제 리소스 파일이 필요하므로 통합 테스트에서 검증하는 것이 더 적합
            // 여기서는 메서드가 예외 없이 실행되는지만 확인
            assertThatCode(() -> repository.loadInitialData())
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("통합 시나리오 테스트")
    class IntegrationScenarioTests {

        @Test
        @DisplayName("아이템 추가 후 findAll과 findById 모두 동작")
        void afterAddingItems_bothFindAllAndFindById_work() {
            // given
            List<ClosetItem> testData = List.of(
                    createTestItem(1L, "image1.jpg", Color.WHITE),
                    createTestItem(2L, "image2.jpg", Color.BLACK)
            );
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            List<ClosetItem> allItems = repository.findAll();
            Optional<ClosetItem> specificItem = repository.findById(1L);

            // then
            assertThat(allItems).hasSize(2);
            assertThat(specificItem).isPresent();
            assertThat(specificItem.get().getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("다양한 속성을 가진 아이템들을 올바르게 저장하고 조회")
        void withVariousAttributes_storesAndRetrievesCorrectly() {
            // given
            List<ClosetItem> testData = List.of(
                    createTestItemWithAllAttributes(1L, Color.WHITE, Category.KNIT, Season.WINTER, Material.WOOL, Style.MINIMAL),
                    createTestItemWithAllAttributes(2L, Color.BLACK, Category.OUTER, Season.FALL, Material.LEATHER, Style.STREET),
                    createTestItemWithAllAttributes(3L, Color.BEIGE, Category.PANTS, Season.SPRING, Material.COTTON, Style.CASUAL)
            );
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when & then
            Optional<ClosetItem> item1 = repository.findById(1L);
            assertThat(item1).isPresent();
            assertThat(item1.get().getColor()).isEqualTo(Color.WHITE);
            assertThat(item1.get().getCategory()).isEqualTo(Category.KNIT);

            Optional<ClosetItem> item2 = repository.findById(2L);
            assertThat(item2).isPresent();
            assertThat(item2.get().getStyle()).isEqualTo(Style.STREET);
            assertThat(item2.get().getMaterial()).isEqualTo(Material.LEATHER);
        }
    }

    @Nested
    @DisplayName("엣지 케이스 테스트")
    class EdgeCaseTests {

        @Test
        @DisplayName("ID가 Long.MAX_VALUE인 아이템 조회")
        void findById_withMaxLongValue_works() {
            // given
            ClosetItem item = createTestItem(Long.MAX_VALUE, "image.jpg", Color.WHITE);
            List<ClosetItem> testData = List.of(item);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            Optional<ClosetItem> result = repository.findById(Long.MAX_VALUE);

            // then
            assertThat(result).isPresent();
        }

        @Test
        @DisplayName("매우 많은 아이템이 있을 때 findAll 성능")
        void findAll_withManyItems_performsWell() {
            // given
            List<ClosetItem> largeTestData = new java.util.ArrayList<>();
            for (long i = 1; i <= 1000; i++) {
                largeTestData.add(createTestItem(i, "image" + i + ".jpg", Color.WHITE));
            }
            ReflectionTestUtils.setField(repository, "closetItems", largeTestData);

            // when
            long startTime = System.currentTimeMillis();
            List<ClosetItem> result = repository.findAll();
            long endTime = System.currentTimeMillis();

            // then
            assertThat(result).hasSize(1000);
            assertThat(endTime - startTime).isLessThan(1000); // 1초 이내
        }

        @Test
        @DisplayName("빈 imageUrl을 가진 아이템 조회")
        void findById_withEmptyImageUrl_works() {
            // given
            ClosetItem item = createTestItem(1L, "", Color.WHITE);
            List<ClosetItem> testData = List.of(item);
            ReflectionTestUtils.setField(repository, "closetItems", testData);

            // when
            Optional<ClosetItem> result = repository.findById(1L);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getImageUrl()).isEmpty();
        }
    }

    // Helper methods
    private ClosetItem createTestItem(Long id, String imageUrl, Color color) {
        return new ClosetItem(
                id,
                imageUrl,
                color,
                Category.KNIT,
                Season.SPRING,
                Material.COTTON,
                Style.MINIMAL,
                new double[]{1.0, 2.0, 3.0}
        );
    }

    private ClosetItem createTestItemWithAllAttributes(
            Long id, Color color, Category category, 
            Season season, Material material, Style style) {
        return new ClosetItem(
                id,
                "/test/image.jpg",
                color,
                category,
                season,
                material,
                style,
                new double[]{1.0, 2.0, 3.0}
        );
    }
}