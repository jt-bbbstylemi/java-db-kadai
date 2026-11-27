package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Connection con = null;
		PreparedStatement statement = null;
		PreparedStatement selectStatement = null;
		ResultSet result = null;

		// ユーザーリスト
		String[][] userList = {
				{ "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
				{ "1002", "2023-02-08", "お疲れ様です！", "12" },
				{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
				{ "1002", "2023-02-10", "明日から連休ですね！", "20" }
		};

		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"msmakenoise6027");

			System.out.println("データベース接続成功：" + con);

			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
			statement = con.prepareStatement(sql);

			System.out.println("レコード追加を実行します");

			int rowCnt = 0;
			for (int i = 0; i < userList.length; i++) {

				statement.setString(1, userList[i][0]); // user_id
				statement.setString(2, userList[i][1]); // posted_at
				statement.setString(3, userList[i][2]); // post_content
				statement.setString(4, userList[i][3]); // likes

				rowCnt += statement.executeUpdate();
			}

			System.out.println(rowCnt + "件のレコードが追加されました");

			String selectSql = "SELECT * FROM posts WHERE user_id = ? ORDER BY post_id ";
			selectStatement = con.prepareStatement(selectSql);
			selectStatement.setInt(1, 1002);
			result = selectStatement.executeQuery();
			System.out.println("ユーザーIDが1002のレコードを検索しました");

			int count = 0;

			while (result.next()) {

				count++;

				System.out.println(
						count + "件目：" +
								"投稿日時=" + result.getDate("posted_at") +
								"／投稿内容=" + result.getString("post_content") +
								"／いいね数=" + result.getInt("likes"));
			}

		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			if (result != null) {
				try {
					result.close();
				} catch (SQLException ignore) {
				}
			}
			if (selectStatement != null) {
				try {
					selectStatement.close();
				} catch (SQLException ignore) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}
}