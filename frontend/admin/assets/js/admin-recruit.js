// ===== 募集要項描画処理 =====
import { jobPostings } from "../../../common/assets/js/job-postings.js";
jobPostings.forEach(job => {
  const jobList = document.getElementById("job-description-table");
  const wrapper = document.createElement("div");
  wrapper.className = "job-item";

  wrapper.innerHTML = `
    <table class="job-table">
      <tbody>
        <tr>
          <th>募集職種</th>
          <td>${job.position}</td>
        </tr>
        <tr>
          <th>雇用形態</th>
          <td>${job.employment_type}</td>
        </tr>
        <tr>
          <th>仕事内容</th>
          <td class="job-text">${nl2br(job.job_description)}</td>
        </tr>
        <tr>
          <th>給与</th>
          <td class="job-text">${nl2br(job.salary)}</td>
        </tr>
        <tr>
          <th>勤務地</th>
          <td>${job.location}</td>
        </tr>
        <tr>
          <th>応募条件</th>
          <td class="job-text">${nl2br(job.requirements)}</td>
        </tr>
        <tr>
          <th>勤務時間</th>
          <td class="job-text">${nl2br(job.work_schedule)}</td>
        </tr>
        <tr>
          <th>待遇・福利厚生</th>
          <td class="job-text">${nl2br(job.benefit)}</td>
        </tr>
        <tr>
          <th>備考</th>
          <td class="job-text">${nl2br(job.notes)}</td>
        </tr>
      </tbody>
    </table>
  `;

  jobList.appendChild(wrapper);
})

// ===== /nを<br>に変換 =====
function nl2br(text) {
  return text.replace(/\n/g, "<br>");
}
