package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /**
     * 구현클래스를 먼저 만들지않고 test 먼저 만들어서
     * 실제로 데이터들어오는지 틀을 만드는것. 테스트 주도개발 TDD
     * */

    @AfterEach //test끝날때마다 실행. test 는 메소드 순서에 상관없이 실행하므로 하나의 테스트 끝날때마다 저장소 데이터 삭제
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);


        Member result = repository.findById(member.getId()).get();
        System.out.println("result : "+result);

        //Assertions.assertEquals(member,result); //동일
        Assertions.assertThat(member).isEqualTo(result); //동일 member가 result와 같음
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);


    }

}

