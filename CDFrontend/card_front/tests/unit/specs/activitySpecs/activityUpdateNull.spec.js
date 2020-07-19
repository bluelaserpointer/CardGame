jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({data: false})),
  post: jest.fn(() => Promise.resolve({data: false}))
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import ActivityUpdatePanel from '@/components/edit/ActivityUpdatePanel'
import Element from 'element-ui';
import axios from 'axios';
import moment from 'moment';

const localVue = createLocalVue();
localVue.use(Element);

describe('ActivityUpdatePanel.vue', () => {
  const wrapper = mount(ActivityUpdatePanel, {
    localVue,
    propsData:{
      updateContent: {
        activity: 0,
        activityImg : '0',
        activityName : '0',
        activityDescription : '0',
        type : 'true',
        start : '0',
      }
    }
  });

  it('Activity Update Panel Nulls submitForm', () => {
    wrapper.vm.limit = false;
    expect(axios.post).toHaveBeenCalledTimes(0);
    wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  it('Activity Entity Panel Nulls confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  it('Activity Entity Panel Nulls deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(axios.post).toHaveBeenCalledTimes(3);
  });

});
