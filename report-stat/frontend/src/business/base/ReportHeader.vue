<template>
  <div class="ms-header">
    <el-row>
      <div class="ms-div">
        {{ title }}
        <span v-if="historyReportName" class="history-report-name">
          {{ historyReportName }}
        </span>
      </div>
      <div class="ms-header-right">
        <el-button
          type="primary"
          v-if="isSaveAsButtonShow"
          size="mini"
          @click="handleSaveAs"
          :disabled="readOnly"
        >
          {{ $t("commons.save_as") }}<i class="el-icon-files el-icon--right"></i
        ></el-button>
        <el-button
          type="primary"
          v-if="isSaveButtonShow"
          size="mini"
          @click="handleSave"
          :disabled="readOnly"
        >
          {{ $t("commons.save") }}<i class="el-icon-files el-icon--right"></i
        ></el-button>
        <el-button
          type=""
          size="mini"
          @click="handleExport"
          :disabled="readOnly"
          >{{ $t("report.export")
          }}<i class="el-icon-download el-icon--right"></i
        ></el-button>
        <span class="ms-span">|</span>
        <i class="el-icon-close report-alt-ico" @click="close" />
      </div>
    </el-row>
  </div>
</template>

<script>
import { hasPermission } from "metersphere-frontend/src/utils/permission";
// import html2canvas from "html2canvas";

export default {
  name: "ReportHeader",
  components: {},
  data() {
    return {};
  },
  props: {
    title: String,
    historyReportId: String,
    historyReportName: String,
  },
  watch: {
    historyReportName() {},
  },
  created() {},
  computed: {
    readOnly() {
      return !hasPermission("PROJECT_REPORT_ANALYSIS:READ+EXPORT");
    },
    isSaveAsButtonShow() {
      if (
        !this.historyReportId ||
        this.historyReportId === null ||
        this.historyReportId === ""
      ) {
        return false;
      } else {
        if (hasPermission("PROJECT_REPORT_ANALYSIS:READ+CREATE")) {
          return true;
        } else {
          return false;
        }
      }
    },
    isSaveButtonShow() {
      if (hasPermission("PROJECT_REPORT_ANALYSIS:READ+UPDATE")) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    handleExport() {
      this.$emit("exportReport", this.title);
    },
    handleSave() {
      if (
        !this.historyReportId ||
        this.historyReportId === null ||
        this.historyReportId === ""
      ) {
        this.$emit("selectAndSaveReport");
      } else {
        this.$emit("updateReport");
      }
    },
    handleSaveAs() {
      this.$emit("selectAndSaveReport");
    },
    close() {
      this.$emit("closePage");
    },
  },
};
</script>

<style scoped>
.ms-header {
  border-bottom: 1px solid #e6e6e6;
  background-color: #fff;
}

.ms-div {
  float: left;
  margin-left: 20px;
  margin-top: 12px;
}

.ms-span {
  margin: 0px 10px 10px;
}

.ms-header-right {
  float: right;
  /*width: 320px;*/
  margin-bottom: 10px;
  margin-top: 10px;
  margin-right: 20px;
}

.report-alt-ico {
  font-size: 17px;
  top: auto;
}

.report-alt-ico:hover {
  color: black;
  cursor: pointer;
  font-size: 18px;
}

.history-report-name {
  margin-left: 20px;
  color: var(--primary_color);
}
</style>
