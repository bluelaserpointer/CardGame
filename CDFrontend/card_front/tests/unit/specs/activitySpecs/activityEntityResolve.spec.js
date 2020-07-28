jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({data: [{
      activityId: 0,
      activityName: '',
      start: '',
      type: false,
      activityDetails: {
        activityImg: '',
        activityDescription: '',
      }
    }]})),
  post: jest.fn(() => Promise.resolve())
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import ActivityEntityPanel from '@/components/edit/ActivityEntityPanel'
import Element from 'element-ui';
import moment from 'moment';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('ActivityEntityPanel.vue', () => {
  const wrapper = shallowMount(ActivityEntityPanel,
    {
      localVue
    });

  it('Activity Entity Panel Resolves created getList watchList', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.list).toStrictEqual([{
      activityId: 0,
      activityName: '',
      activityImg: '',
      activityDescription: '',
      start: '',
      type: false,
      activityDetails: {
        activityImg: '',
        activityDescription: '',
      }
    }]);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/activity/getAllActivities');
  });

  it('Activity Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.dialogStatus).toBe('update');
  });

  it('Activity Entity Panel Resolves formatDate', () => {
    let date = new Date();
    let formatDateStr = moment(wrapper.vm.formatDate(date)).format('YYYY-MM-DD HH:mm:ss');
    expect(wrapper.vm.formatDate(date)).toBe(formatDateStr);
  });

});
