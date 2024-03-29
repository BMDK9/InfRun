package infrun.hellospring.service;

import infrun.hellospring.domain.Member;
import infrun.hellospring.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
//@Transactional
public class MemberService {


    private final MemberRepository memberRepository;

//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member signIn(Member member) {
        // 공통 로직과 핵심 로직이 혼재 되어있어 유지보수 힘듦
        long start = System.currentTimeMillis();

        try {
        validateDuplicateMember(member); // 중복 회원 검증
        return memberRepository.save(member);
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("서비스에 박아 놓은 AOP = " + timeMs + "ms");
        }
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOneMember(Member member) {
        return memberRepository.findById(member.getId());
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
