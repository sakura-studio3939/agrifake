// ===== 募集要項仮データ =====
// フロントエンド実装・UI確認用
export const jobPostings = [
  {
    id: "1",
    position: "農業スタッフ",
    employment_type: "正社員",
    job_description: "農作物の生産・管理・出荷作業を中心に、農場運営に関わる業務全般を行っていただきます。\n季節ごとの作業を通して、自然と向き合いながら農業の基礎から学べる環境です。",
    salary: "月給 200,000円〜\n※経験・能力を考慮の上、決定します",
    location: "XX県YY市ZZ町1丁目2番3号X県YY市",
    requirements: "学歴・経験不問\n農業に興味があり、前向きに取り組める方",
    work_schedule: "8:00〜17:00（休憩1時間）\n週休2日制（シフト制）",
    benefit: "各種社会保険完備、交通費支給、作業着貸与",
    notes: "未経験の方でも丁寧に指導しますので、安心してご応募ください。",
    created_at: "2026年2月3日",
    updated_at: "2026年2月5日"
  },
  {
    id: "2",
    position: "農業生産スタッフ",
    employment_type: "正社員・パート（応相談）",
    job_description: "田畑での農作業や、収穫した農作物の選別・梱包などをお任せします。\n自然のリズムに合わせた仕事なので、体を動かすことが好きな方にぴったりです。",
    salary: "時給 1,100円〜（パート）\n月給 190,000円〜（正社員）",
    location: "XX県YY市ZZ町1丁目2番3号X県YY市",
    requirements: "学歴・経験不問<br>未経験歓迎\n農業に興味があり、前向きに取り組める方",
    work_schedule: "勤務時間・日数は相談可\n農繁期・農閑期に応じたシフト制",
    benefit: "社会保険完備（勤務条件による）、社員割引あり",
    notes: "農業が初めての方でも、先輩スタッフがしっかりサポートします。",
    created_at: "2026年2月4日",
    updated_at: "2026年2月6日"
  }
]

// ===== 募集要項描画処理 =====
const renderJobPostings = (list, id) => {
  const el = document.getElementById(id);
  if (!el) return;
  if (el.children.length > 0) return;

  list.forEach(job => {
    const wrapper = document.createElement('div');
    wrapper.className = 'job-item';
    wrapper.innerHTML = `
      <table class="job-table">
        <tbody>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">募集職種</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.position}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">雇用形態</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.employment_type}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">仕事内容</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.job_description.replace(/\n/g,'<br>')}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">給与</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.salary.replace(/\n/g,'<br>')}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">勤務地</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.location}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">応募条件</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.requirements.replace(/\n/g,'<br>')}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">勤務時間</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.work_schedule.replace(/\n/g,'<br>')}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">待遇・福利厚生</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.benefit.replace(/\n/g,'<br>')}</td>
          </tr>
          <tr class="recruit__table-row">
            <th class="recruit__table-cell recruit__table-cell--label">備考</th>
            <td class="recruit__table-cell recruit__table-cell--value">${job.notes.replace(/\n/g,'<br>')}</td>
          </tr>
        </tbody>
      </table>
    `;
    el.appendChild(wrapper);
  });
};

// ===== DOMロード時に描画 =====
document.addEventListener("DOMContentLoaded", () => {
  renderJobPostings(jobPostings, 'job-description-table');
});