<template>
  <div>
    <el-dropdown @command="handleCommand" class="scenario-ext-btn">
      <el-link type="primary" :underline="false">
        <el-icon class="el-icon-more"></el-icon>
      </el-link>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="ref">{{ $t('api_test.automation.view_ref') }}</el-dropdown-item>
        <el-dropdown-item command="schedule" v-permission="['PROJECT_API_SCENARIO:READ+SCHEDULE']">
          {{ $t('api_test.automation.schedule') }}
        </el-dropdown-item>
        <el-dropdown-item
          command="create_performance"
          v-permission="['PROJECT_API_SCENARIO:READ+CREATE_PERFORMANCE']"
          v-modules="['performance']">
          {{ $t('api_test.create_performance_test') }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <ms-schedule-maintain ref="scheduleMaintain" @refreshTable="refreshTable" :request="request" />
  </div>
</template>

<script>
import { genPerformanceTestJmx } from '@/api/scenario';
import MsScheduleMaintain from '@/business/automation/schedule/ScheduleMaintain';
import { getCurrentProjectID } from 'metersphere-frontend/src/utils/token';
import { hasPermission } from 'metersphere-frontend/src/utils/permission';
import { getUUID } from 'metersphere-frontend/src/utils';
import { usePerformanceStore } from '@/store';

const performanceStore = usePerformanceStore();

export default {
  name: 'MsScenarioExtendButtons',
  components: { MsScheduleMaintain },
  props: {
    row: Object,
    request: {},
  },
  methods: {
    hasPermission,
    handleCommand(cmd) {
      switch (cmd) {
        case 'ref':
          this.$emit('showCaseRef', this.row);
          break;
        case 'schedule':
          this.$emit('openSchedule');
          this.$refs.scheduleMaintain.open(this.row);
          break;
        case 'create_performance':
          this.createPerformance(this.row);
          break;
      }
    },
    createPerformance(row) {
      if (!hasPermission('PROJECT_PERFORMANCE_TEST:READ+CREATE')) {
        this.$warning(this.$t('api_test.create_performance_test_tips'));
        return;
      }
      this.infoDb = false;
      let run = {};
      let scenarioIds = [];
      scenarioIds.push(row.id);
      run.projectId = getCurrentProjectID();
      run.ids = scenarioIds;
      run.id = getUUID();
      run.name = row.name;
      genPerformanceTestJmx(run).then((response) => {
        let jmxInfo = response.data.jmxInfoDTO;
        if (jmxInfo) {
          let projectEnvMap = response.data.projectEnvMap;
          let jmxObj = {};
          jmxObj.name = jmxInfo.name;
          jmxObj.xml = jmxInfo.xml;
          jmxObj.attachFiles = jmxInfo.attachFiles;
          jmxObj.attachByteFiles = jmxInfo.attachByteFiles;
          jmxObj.scenarioId = row.id;
          jmxObj.caseId = null;
          jmxObj.version = row.version;
          jmxObj.projectEnvMap = projectEnvMap;
          performanceStore.$patch({ test: { name: row.name, jmx: jmxObj }, scenarioJmxs: null });
          this.$router.push({
            path: '/performance/test/create',
          });
        }
      });
    },
    openScenario(item) {
      this.$emit('openScenario', item);
    },
    refreshTable() {
      this.$emit('refreshTable');
    },
  },
};
</script>

<style scoped>
.scenario-ext-btn {
  margin-left: 10px;
}
</style>
