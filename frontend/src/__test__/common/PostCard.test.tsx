//1. 렌더링 년차가 없다면 신입으로 렌더링
//2. 기술 스택은 3가지만
//3. 기술 스택이 없다면 NoSkill로 렌더링

import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import PostCard from "../../components/common/PostCard";
import { describe, it, expect, vi } from "vitest";

// 이미지 모킹
vi.mock("../../assets/avatar.png", () => ({
  default: "avatar.png",
}));
vi.mock("../../assets/baggage3.svg", () => ({
  default: "baggage3.svg",
}));
vi.mock("../../assets/pngegg2.svg", () => ({
  default: "pngegg2.svg",
}));

describe("PostCard 컴포넌트 테스트", () => {
  it("PostCard 컴포넌트 렌더링 확인", () => {
    // Given: 사용자가 포스트 카드를 확인하고 있다.
    const props = {
      name: "홍길동",
      role: "Frontend",
      experience: 3,
      education: "전공자",
      skills: ["React", "TypeScript", "Redux"],
      onClick: vi.fn(),
    };

    // When: 포스트 카드가 렌더링 되어있을 때
    render(<PostCard {...props} />);

    // Then : 모든 정보가 렌더링 되어있어야 한다.
    // 이름 확인
    expect(screen.getByText("홍길동")).toBeInTheDocument();
    // 역할 확인
    expect(screen.getByText("Frontend")).toBeInTheDocument();
    // 경력 확인
    expect(screen.getByText("3년")).toBeInTheDocument();
    // 학력 확인
    expect(screen.getByText("전공자")).toBeInTheDocument();
    // 기술 스택 확인
    expect(screen.getByText("React")).toBeInTheDocument();
    expect(screen.getByText("TypeScript")).toBeInTheDocument();
    expect(screen.getByText("Redux")).toBeInTheDocument();
    // 이미지 src 확인
    expect(screen.getByAltText("avatar")).toHaveAttribute("src", "avatar.png");
    expect(screen.getByAltText("baggage")).toHaveAttribute(
      "src",
      "baggage3.svg"
    );
    expect(screen.getByAltText("pngegg")).toHaveAttribute("src", "pngegg2.svg");
  });

  it("신입인 경우 렌더링 테스트", () => {
    // Given : 신입 개발자인 경우
    const props = {
      name: "박복순",
      role: "Backend",
      experience: 0,
      education: "전공자",
      skills: ["Java"],
      onClick: vi.fn(),
    };

    // When : 렌더링 되었을 때
    render(<PostCard {...props} />);

    // Then : 경력이 '신입'으로 표시되어야 한다.
    expect(screen.getByText("신입")).toBeInTheDocument();
  });

  it("기술 스택이 비어있을 경우 테스트", () => {
    // Given : 기술 스택이 비어있는 경우
    const props = {
      name: "박복순",
      role: "Backend",
      experience: 0,
      education: "전공자",
      skills: ["Java"],
      onClick: vi.fn(),
    };

    // When : 렌더링 되었을 때
    render(<PostCard {...props} />);

    // Then : NoSkill이 표시되어야 한다.
    expect(screen.getByText("NoSkill")).toBeInTheDocument();
  });

  it("onClick 이벤트 테스트", () => {
    // Given : 클릭 이벤트가 발생했을 때
    const handleClick = vi.fn();
    const props = {
      name: "박복순",
      role: "Backend",
      experience: 0,
      education: "전공자",
      skills: ["Java"],
      onClick: vi.fn(),
    };

    // When : 카드를 클릭했을 때
    render(<PostCard {...props} />);
    const card = screen.getByTestId("post-card");
    fireEvent.click(card);

    // Then : onClick 함수가 호출되어야 한다.
    expect(handleClick).toHaveBeenCalledTimes(1);
  });

  it("기술 스택이 3개를 초과하는 경우 테스트", () => {
    // Given : 기술 스택이 3개 초과인 경우
    const props = {
      name: "홍길동",
      role: "Frontend",
      experience: 3,
      education: "전공자",
      skills: ["React", "Redux", "Node.js", "TypeScript"],
      onClick: vi.fn(),
    };

    // When : 렌더링 되었을 때
    render(<PostCard {...props} />);

    // Then : 세 개의 기술 스택만 렌더링 되어야 한다.
    expect(screen.getByText("React")).toBeInTheDocument();
    expect(screen.getByText("Redux")).toBeInTheDocument();
    expect(screen.getByText("Node.js")).toBeInTheDocument();
    // 네 번째 기술은 렌더링되지 않아야 함
    expect(screen.queryByText("TypeScript")).not.toBeInTheDocument();
  });
});
