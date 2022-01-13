package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
// memory 모드로 db까지 엮어서 테스트 해야 하므로 사용
// 위의 2가지 annotation이 있어서 spring integration 테스트 가능
@Transactional // 데이터를 변경해야 하기 때문에 사용 // 이게 있어야 rollback이 된다
public class MemberServiceTest { // JUNIT 4 사용

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    // 테스크 케이스니까 가장 간단하게 이렇게 작성하면 됨
    @Autowired
    EntityManager em;

    @Test
//    @Rollback(value = false) // 등록 쿼리 보고 싶을 때 사용
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다!!! 똑같은 이름을 넣었으니까
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다!!! 똑같은 이름을 넣었으니까
//        } catch (IllegalStateException e) {
//            return;
//        }
//        (expected = IllegalStateException.class) 작성으로 필요 없어짐

        // then
        fail("예외가 발생해야 한다.");
    }

}