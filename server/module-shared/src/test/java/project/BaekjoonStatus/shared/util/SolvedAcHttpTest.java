package project.BaekjoonStatus.shared.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class SolvedAcHttpTest {
    private SolvedAcHttp solvedAcHttp;

    @BeforeEach
    public void beforeEach() {
        solvedAcHttp = new SolvedAcHttp();
    }

    @Test
    public void 문제_정보_가져오기() throws Exception {
        SolvedAcProblemResp info = solvedAcHttp.getProblemByProblemId(1000L);

        assertThat(info.getTitleKo())
                .isEqualTo("A+B");
    }

    @Test
    public void 올바르지_문제_ID() throws Exception {
        MyException myException = catchThrowableOfType(() -> solvedAcHttp.getProblemByProblemId(-1L), MyException.class);

        assertThat(myException.getMessage())
                .isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getMessage());
        assertThat(myException.getCode())
                .isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getCode());
    }

    @Test
    public void 청크_단위_문제_정보_가져오기() throws Exception {
        List<Long> problemIds = getProblemIds(1099L);
        List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(problemIds);

        assertThat(infos)
                .hasSize(problemIds.size());

        List<Long> infoIds = infos.stream()
                .map(SolvedAcProblemResp::getProblemId)
                .collect(Collectors.toList());

        assertThat(infoIds)
                .doesNotHaveDuplicates();
        assertThat(infoIds)
                .containsAnyElementsOf(problemIds);
    }

    @Test
    public void 청크_단위_문제_가져올때_사이즈가_100이상일_떄() throws Exception {
        List<Long> size101 = getProblemIds(1100L);
        List<Long> size200 = getProblemIds(1199L);

        MyException myException = catchThrowableOfType(() -> solvedAcHttp.getProblemsByProblemIds(size101), MyException.class);
        assertThat(myException.getMessage())
                .isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getMessage());
        assertThat(myException.getCode())
                .isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getCode());

        myException = catchThrowableOfType(() -> solvedAcHttp.getProblemsByProblemIds(size200), MyException.class);
        assertThat(myException.getMessage())
                .isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getMessage());
        assertThat(myException.getCode())
                .isEqualTo(CodeEnum.SOLVED_AC_SERVER_ERROR.getCode());
    }

    @Test
    public void 청크_단위_문제_가져올때_올바르지_않은_문제_ID() throws Exception {
        List<Long> problemIds = getProblemIds(1010L);
        problemIds.add(-1L);

        List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(problemIds);

        assertThat(infos)
                .doesNotHaveDuplicates();
        assertThat(infos)
                .hasSize(problemIds.size() - 1);

        List<Long> infoIds = infos.stream()
                .map(SolvedAcProblemResp::getProblemId)
                .collect(Collectors.toList());

        assertThat(infoIds)
                .doesNotContain(-1L);

        problemIds.add(2000L);
        infos = solvedAcHttp.getProblemsByProblemIds(problemIds);

        assertThat(infos)
                .hasSize(problemIds.size() - 1);

        infoIds = infos.stream()
                .map(SolvedAcProblemResp::getProblemId)
                .collect(Collectors.toList());

        assertThat(infoIds)
                .doesNotContain(-1L);
    }

    private List<Long> getProblemIds(Long max) {
        List<Long> ret = new ArrayList<>();
        for (long i = 1000L; i <= max; i++) {
            ret.add(i);
        }

        return ret;
    }
}
