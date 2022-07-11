# android-repo-04

# 규칙
- 네이밍 컨벤션
  - 변수 및 함수 : 카멜
  - view id : {type}\_{page}\_{name}
    - layout : type을 layout으로 작성
    - TextView : type을 text으로 작성
    - EditText : type을 edit으로 작성
    - Button : type을 btn으로 작성
    - ImageView : type을 img로 작성
  - string resource : {page}_{name}
--------
- 패턴 및 구조
  - MVVM 패턴
  - 최상위 패키지 : data / view / viewmodel / api / util  
--------
  
- Feature
  - api / repo (페어 프로그래밍)
  - 로그인 (페어 프로그래밍)
  - 검색 (페어 프로그래밍)
  - 메인 / Issue : 최광현
  - Nofication / Profile : 김민지
---------
- 커밋 메시지 
  - [Header] [Feature] [Content]
    - 헤더
      - feat	새로운 기능 추가
      - fix	버그 수정
      - docs	문서 수정
      - style	공백, 세미콜론 등 스타일 수정
      - refactor	코드 리팩토링
      - perf	성능 개선
      - test	테스트 추가
      - chore	빌드 과정 또는 보조 기능(문서 생성기능 등) 수정
----------
- 브랜치 이름
  - feat / 화면이름
