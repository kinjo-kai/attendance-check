package util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Attendance;
import model.CheckResult;

public class CsvUtil {

    /**
     * 勤怠CSV読込
     */
    public static List<Attendance> readAttendanceCsv(String filePath) throws Exception {

        List<Attendance> list = new ArrayList<>();

        try (BufferedReader br = EncodingUtil.createReader(filePath)) {

            String line;
            boolean isHeader = true;
            int lineNo = 0;

            while ((line = br.readLine()) != null) {

                lineNo++;

                // ヘッダー除外
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // 空行チェック
                if (line.trim().isEmpty()) {
                    LoggerUtil.getLogger()
                            .warning("空行スキップ 行番号=" + lineNo);
                    continue;
                }

                String[] data = line.split(",");

                // 必須列数チェック
                if (data.length < 3) {
                    LoggerUtil.getLogger()
                            .warning("列不足スキップ 行番号=" + lineNo);
                    continue;
                }

                Attendance attendance = new Attendance(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data.length >= 4 ? data[3].trim() : ""
                );

                list.add(attendance);
            }
        }

        return list;
    }

    /**
     * エラーCSV出力
     */
    public static void writeErrorCsv(
            String filePath,
            List<CheckResult> resultList) throws Exception {

        try (PrintWriter pw =
                     new PrintWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream(filePath),
                                     "UTF-8"))) {

            // ヘッダー
            pw.println("社員ID,日付,ステータス,備考");

            for (CheckResult r : resultList) {

                if ("エラー".equals(r.getStatus())) {

                    pw.println(
                            r.getEmployeeId() + "," +
                                    r.getDate() + "," +
                                    r.getStatus() + "," +
                                    r.getRemark()
                    );
                }
            }
        }
    }

    /**
     * 結果CSV出力
     */
    public static void writeResultCsv(
            String filePath,
            List<CheckResult> results) throws Exception {

        try (PrintWriter pw =
                     new PrintWriter(
                             new OutputStreamWriter(
                                     new FileOutputStream(filePath),
                                     "UTF-8"))) {

            pw.println("社員ID,日付,判定,備考");

            for (CheckResult r : results) {
                pw.println(r.toCsvLine());
            }
        }
    }

    /**
     * 集計CSV出力
     */
    public static void writeSummaryCsv(
            String filePath,
            Map<String, int[]> summary) throws Exception {

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(filePath), "UTF-8"))) {

            pw.println("社員ID,出勤日数,遅刻回数,エラー回数");

            for (Map.Entry<String, int[]> entry : summary.entrySet()) {

                String employeeId = entry.getKey();
                int[] counts = entry.getValue();

                pw.println(
                        employeeId + "," +
                        counts[0] + "," +
                        counts[1] + "," +
                        counts[2]
                );
            }
        }
    }
}