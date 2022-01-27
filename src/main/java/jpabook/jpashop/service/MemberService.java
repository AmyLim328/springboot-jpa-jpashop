package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 기본적으로는 이게 작동 // readOnly = true : 조회(읽기)에 최적화
@RequiredArgsConstructor // final 있는 필드만 가지고 생성자 만들어 줌
public class MemberService {

    private final MemberRepository memberRepository; // 변경할 일이 없으니 final 사용 권장

//    @Autowired
//    private MemberRepository memberRepository;
//    field injection
//    단점이 많다
//    못 바꾼다 (field, private)

//    private MemberRepository memberRepository;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//     setter injection
//     장점 : 테스트 코드 작성 시 mock 직접 주입 가능
//     단점 : 실제 application 돌아가는 시점에 누군가가 바꿀 수 있음

//    private final MemberRepository memberRepository; // 변경할 일이 없으니 final 사용 권장

//    @Autowired // 생성자 하나면 생략 가능
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//     생성자 injection : 생성자에서 injection 해준다
//     권장하는 방법
//     한 번 생성할 때 완성이 되기 때문에 중간에 set해서 바꿀 수 없다
//     test case 작성할 때 놓치지 않고 명확하게 작성할 수 있다

   /**
     * 회원가입
     */
    @Transactional // 따로 설정한 건 우선권 가지기 때문에 이게 동작 // 쓰기에는 readOnly = true 절대 작성하면 안됨
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId(); // id라도 돌려줘야 뭐가 저장됐는지 알 수 있다
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    // 동시에 같은 이름으로 가입할 수 있는 상황을 고려해 비지니스 로직이 이렇게 있더라도
    // 데이터베이스에서 member.getName()에 unique 제약 조건으로 잡아주는 것이 좋다 (최후의 방어)

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한 명 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}