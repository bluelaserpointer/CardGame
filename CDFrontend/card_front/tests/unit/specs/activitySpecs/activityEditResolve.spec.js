jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve()),
  post: jest.fn(() => Promise.resolve({data: true}))
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import ActivityEditPanel from '@/components/edit/ActivityEditPanel'
import Element from 'element-ui';
import axios from 'axios';
import moment from 'moment';

const localVue = createLocalVue();
localVue.use(Element);

describe('ActivityEditPanel.vue', () => {
  const wrapper = mount(ActivityEditPanel, {
    localVue
  });

  it('Activity Edit Panel Resolves created', () => {
    expect(wrapper.vm.postForm.image_uri).toBe('');
    expect(wrapper.vm.postForm.title).toBe('');
    expect(wrapper.vm.postForm.content).toBe('');
    expect(wrapper.vm.limit).toBeFalsy();
    expect(wrapper.vm.displayTime).toBe(undefined);
  });

  it('Activity Edit Panel Resolves submitForm branch1', async () => {
    wrapper.vm.limit = false;
    expect(axios.post).toHaveBeenCalledTimes(0);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  it('Activity Edit Panel Resolves submitForm branch2', async() => {
    wrapper.vm.limit = true;
    wrapper.vm.displayTime = '2000-01-01 00:00:00';

    expect(axios.post).toHaveBeenCalledTimes(1);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  it('Activity Edit Panel Resolves submitForm branch3', async() => {
    wrapper.vm.limit = true;
    wrapper.vm.displayTime = undefined;

    expect(axios.post).toHaveBeenCalledTimes(2);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(3);
  });

  it('Activity Edit Panel Resolves formatDate', () => {
    let date = new Date();
    let formatDateStr = moment(wrapper.vm.formatDate(date)).format('YYYY-MM-DD HH:mm:ss');
    expect(wrapper.vm.formatDate(date)).toBe(formatDateStr);
  });

  it('Activity Edit Panel Resolves submitForm branch4', async() => {
    wrapper.vm.postForm.image_uri = undefined;
    wrapper.vm.postForm.title = undefined;
    wrapper.vm.postForm.content = undefined;

    expect(axios.post).toHaveBeenCalledTimes(3);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(4);
  });
});
