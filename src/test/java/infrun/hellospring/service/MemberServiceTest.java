package infrun.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import infrun.hellospring.domain.Member;
import infrun.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("회원가입")
    void signIn() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Member saveTestMember = memberService.signIn(member);
        Member result = memberService.findOneMember(saveTestMember).get();

        // then
        assertThat(result.getName()).isEqualTo(member.getName());
    }

    @DisplayName("회원가입 예외(이름 중복)")
    @Test
    void test() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when then
        memberService.signIn(member1);
        IllegalStateException message = assertThrows(IllegalStateException.class,
                () -> memberService.signIn(member2),
                "회원 가입 중복 테스트 실패 : 다른 예외 발생");

        assertThat(message.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }


    @Test
    void findAllMembers() {
    }

    @Test
    void findOneMember() {
    }
}