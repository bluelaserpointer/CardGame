jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve()),
  post: jest.fn(() => Promise.resolve({data: true}))
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

  it('Activity Update Panel Resolves created', () => {
    expect(wrapper.vm.postForm.image_uri).toBe('0');
    expect(wrapper.vm.postForm.title).toBe('0');
    expect(wrapper.vm.postForm.content).toBe('0');
    expect(wrapper.vm.limit).toBeTruthy();
    expect(wrapper.vm.displayTime).toBe('0');
  });

  it('Activity Update Panel Resolves submitForm branch1', async () => {
    wrapper.vm.limit = false;
    expect(axios.post).toHaveBeenCalledTimes(0);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  it('Activity Update Panel Resolves submitForm branch2', async() => {
    wrapper.vm.limit = true;
    wrapper.vm.displayTime = '2000-01-01 00:00:00';

    expect(axios.post).toHaveBeenCalledTimes(1);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  it('Activity Update Panel Resolves submitForm branch3', async() => {
    wrapper.vm.limit = true;
    wrapper.vm.displayTime = undefined;

    expect(axios.post).toHaveBeenCalledTimes(2);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(3);
  });

  it('Activity Update Panel Resolves formatDate', () => {
    let date = new Date();
    let formatDateStr = moment(wrapper.vm.formatDate(date)).format('YYYY-MM-DD HH:mm:ss');
    expect(wrapper.vm.formatDate(date)).toBe(formatDateStr);
  });

  it('Activity Update Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeTruthy();
    expect(axios.post).toHaveBeenCalledTimes(4);
  });

  it('Activity Update Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    expect(axios.post).toHaveBeenCalledTimes(4);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(5);
  });

  it('Activity Update Panel Resolves submitForm branch4', async() => {
    wrapper.vm.postForm.image_uri = undefined;
    wrapper.vm.postForm.title = undefined;
    wrapper.vm.postForm.content = undefined;

    expect(axios.post).toHaveBeenCalledTimes(5);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(6);
  });
});
