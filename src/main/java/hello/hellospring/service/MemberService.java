package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) { //서비스부를때마다 new 하지않고 외부에서 넣어주는것을 di 라고함
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        //동명이인 중복회원 가입방지

        /*
        사용1
        Optional<Member> result = memberRepository.findByName(member.getName());

        //if null ? 묻지않고 optional 로 감싸서 꺼냄.  ifPresent : 값이 있으면
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); */


        /*
        사용2
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });*/

        //사용 3 위 영역 드래그하고 Ctrl + t => Extract Method 메소드 따로 뗌
        validateName(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateName(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 아이디 반환
     */
    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }

}
