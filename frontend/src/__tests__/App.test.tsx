import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import CommentCard from "../components/comment/CommentCard";
import { it } from "vitest";

describe("일반 코멘트 카드 테스트", () => {
  const mockComment = {
    id: 1,
    text: "임시 댓글",
    timestamp: new Date(),
    modified: false,
  };

  const mockOnDelete = vi.fn();
  const mockOnEdit = vi.fn();

  it("수정 버튼 작동 확인(handleEditToggle)", () => {
    //Given : 사용자가 댓글을 수정하고 싶은 상황
    render(
      <CommentCard
        {...mockComment}
        onDelete={mockOnDelete}
        onEdit={mockOnEdit}
      />
    );
    //When : 수정 버튼을 클릭했을 때
    fireEvent.click(screen.getByText("수정"));
    //Then : 수정할 수 있는 input이 렌더링 되어야 한다.
    expect(screen.getByDisplayValue(mockComment.text)).toBeInTheDocument();
  });
});
