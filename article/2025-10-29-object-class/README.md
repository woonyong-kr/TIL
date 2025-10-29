# Object 클래스

자바의 모든 클래스는 Object 클래스를 상속받는다. 명시적으로 상속을 선언하지 않아도 컴파일러가 자동으로 추가한다.

```java
public class Student { }
// 컴파일 후
public class Student extends Object { }
```

## Object의 역할

### 1. 공통 기능 제공

모든 객체가 필요한 기본 메서드들을 Object에서 제공한다.:
- toString() - 객체를 문자열로 표현
- equals() - 객체의 동등성 비교
- getClass() - 클래스 정보 조회
- hashCode() - 해시값 반환

### 2. 다형성 지원

모든 객체를 Object 타입으로 참조 가능하므로 다양한 타입을 통합적으로 처리할 수 있다.

```java
Object student = new Student("김철수", 20);
Object book = new Book("자바의 정석", 35000);
Object[] container = {student, book};

void display(Object obj) {
    System.out.println(obj.toString());
}
```

## 주요 메서드

### toString()

객체를 문자열로 표현하는 메서드다.

**기본 구현:**
```java
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
// 출력: Student@a1b2c3d
```

기본 toString()은 클래스 이름과 16진수 해시코드로 구성된다. 
필요한 의도에 따라 오버라이딩해서 사용할 수 있다. 
String은 toString()으로 자신이 가지고 있는 문자열을 반환한다.

**오버라이딩:**

```java
public class Student {
    private String name;
    private int score;
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', score=" + score + '}';
    }
}

Student s = new Student("박영희", 95);
System.out.println(s);  // Student{name='박영희', score=95}
```

**System.out.println()과 toString():**

```java
System.out.println(s);           // 내부에서 toString() 자동 호출
System.out.println(s.toString()); // 명시적 호출
// 두 코드는 동일한 결과
```

---

### equals()

두 객체의 동등성을 비교하는 메서드다.

#### 동일성 vs 동등성

**동일성(Identity)**: == 연산자로 같은 메모리 주소를 참조하는지 확인

```java
Student s1 = new Student("이순신", 90);   // 메모리 주소: 0x0001
Student s2 = new Student("이순신", 90);   // 메모리 주소: 0x0002

s1 == s2  // false
```

**동등성(Equality)**: equals()로 객체의 내용이 같은지 확인

```java
s1.equals(s2)  // 구현에 따라 결정됨
```

#### 기본 equals()

```java
public boolean equals(Object obj) {
    return (this == obj);  // 동일성만 비교
}
```

기본 구현은 동일성(메모리 주소)만 비교한다.

#### equals() 오버라이딩

동등성 비교가 필요할 때 equals()를 오버라이딩한다:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return Objects.equals(studentId, student.studentId);
}

Student s1 = new Student("이순신", 90);
s1.setStudentId("A001");

Student s2 = new Student("이순신", 90);
s2.setStudentId("A001");

System.out.println(s1.equals(s2));  // true (같은 학번)
```

#### equals()와 hashCode()

equals()로 같다고 판단된 두 객체는 같은 hashCode()값을 반환해야 한다. 특히 HashMap이나 HashSet 같은 컬렉션을 사용할 때 필수다.

```java
@Override
public int hashCode() {
    return Objects.hash(studentId);
}
```

## Object 타입의 한계

Object 타입으로 모든 객체를 받을 수 있지만, Object에 없는 메서드를 호출하려면 다운캐스팅이 필요하다.

```java
Object obj = new Student("홍길동", 85);

System.out.println(obj.toString());  // 가능

// obj.study();  // 컴파일 에러
// Object에는 study() 메서드가 없음
```

**다운캐스팅:**

```java
if (obj instanceof Student student) {
    student.study();  // 가능
}
```

## Object 다형성 활용 예제

다양한 타입을 Object 타입으로 통합 관리하는 예제:

```java
public class Warehouse {
    private List<Object> items = new ArrayList<>();
    
    public void store(Object item) {
        items.add(item);
        System.out.println("저장 완료: " + item);
    }
    
    public void showInventory() {
        for (Object item : items) {
            System.out.println(item.toString());
        }
    }
}

// 사용
Warehouse warehouse = new Warehouse();
warehouse.store(new Book("파이썬 기초", 25000));
warehouse.store(new Laptop("맥북", 1500000));
warehouse.store(new Notebook("바인더", 5000));

warehouse.showInventory();
// Book{title='파이썬 기초', price=25000}
// Laptop{model='맥북', price=1500000}
// Notebook{pages=200, price=5000}
```

## 기타 메서드

| 메서드 | 설명 |
|--------|------|
| `getClass()` | 객체의 클래스 정보 반환 |
| `hashCode()` | 해시코드 반환 (컬렉션에서 사용) |
| `wait()`, `notify()` | 멀티쓰레드 동기화 제어 |
| `clone()` | 객체 복사 (실무에서 거의 사용하지 않음) |

## 배송 시스템 예제

equals()와 toString() 활용:

```java
public class DeliverySystem {
    private List<Object> history = new ArrayList<>();
    
    public void recordShipment(Object shipment) {
        // 중복 확인
        if (history.contains(shipment)) {
            System.out.println("이미 배송 완료: " + shipment);
            return;
        }
        
        history.add(shipment);
        System.out.println("배송 기록: " + shipment);
    }
}

// 사용
public class Package {
    private String id;
    private String content;
    
    @Override
    public String toString() {
        return "Package{id='" + id + "', content='" + content + "'}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package pkg = (Package) o;
        return Objects.equals(id, pkg.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

DeliverySystem system = new DeliverySystem();
Package pkg1 = new Package();
pkg1.setId("PKG001");

Package pkg2 = new Package();
pkg2.setId("PKG001");

system.recordShipment(pkg1);  // 배송 기록: Package{...}
system.recordShipment(pkg2);  // 이미 배송 완료
```

## 정리

- 모든 클래스는 Object를 상속받는다
- toString()을 오버라이딩하면 객체를 원하는 형태의 문자열로 표현할 수 있다
- equals()는 동등성 비교이고, == 는 동일성 비교다
- equals()를 오버라이딩할 때는 hashCode()도 함께 오버라이딩해야 한다
- Object 다형성을 활용하면 다양한 타입을 통합적으로 처리할 수 있다
- Object 타입에 없는 메서드 호출 시 다운캐스팅이 필요하다