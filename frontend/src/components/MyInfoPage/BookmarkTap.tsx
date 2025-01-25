import { useState, useEffect } from "react";
import BookmarkItem from "../../components/MyInfoPage/BookmarkItem.tsx";
import { getBookmarkById } from "../../api/bookMarkApi.ts";
import { BookmarkType } from "../../dataType.ts";

function BookmarkTap() {
  const [bookmarks, setBookmarks] = useState<BookmarkType[]>([]);

  const userId = 1; // 임시. 지금 userId 필요 없음

  // 북마크 조회 API 호출
  const fetchBookmarks = async () => {
    try {
      const data = await getBookmarkById(userId);
      setBookmarks(data.result);
    } catch (error) {
      console.error("북마크를 가져오는 데 실패했습니다.", error);
    }
  };

  // 북마크가 변경될 때 호출
  const handleBookmarkUpdate = () => {
    fetchBookmarks();
  };

  useEffect(() => {
    fetchBookmarks();
  }, []);

  return (
    <div className="flex gap-8">
      <div className="flex-1">
        <div className="bg-white rounded-lg shadow-sm p-6">
          <div className="flex justify-between items-center mb-6">
            <h3 className="text-lg font-semibold">북마크한 이력서</h3>
            <p className="text-sm text-gray-500">{bookmarks.length}개</p>
          </div>
          <div className="space-y-4">
            {bookmarks.length > 0 ? (
              bookmarks.map((bookmark) => (
                <BookmarkItem
                  key={bookmark.bookmark_id}
                  bookmark={bookmark}
                  onUpdate={handleBookmarkUpdate}
                />
              ))
            ) : (
              <div className="text-center text-gray-500 py-8">
                북마크한 이력서가 없습니다
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default BookmarkTap;
