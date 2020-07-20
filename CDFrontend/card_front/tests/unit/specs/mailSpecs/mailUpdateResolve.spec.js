jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve()),
  post: jest.fn(() => Promise.resolve({data: true}))
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import MailUpdatePanel from '@/components/edit/MailUpdatePanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('MailUpdatePanel.vue', () => {
   const wrapper = mount(MailUpdatePanel, {
    localVue,
     propsData:{
      updateContent: {
        mail: 0,
        mailImg : '0',
        mailName : '0',
        mailDescription : '0',
      }
    }
  });

  it('Mail Update Panel Resolves created', () => {
    expect(wrapper.vm.postForm.image_uri).toBe('0');
    expect(wrapper.vm.postForm.title).toBe('0');
    expect(wrapper.vm.postForm.content).toBe('0');
  });

  it('Mail Update Panel Resolves submitForm', async () => {
    expect(axios.post).toHaveBeenCalledTimes(0);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  it('Mail Entity Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeTruthy();
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  it('Mail Entity Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(3);
  });

  it('Mail Update Panel Resolves submitForm branchLast', async () => {
    wrapper.vm.postForm.image_uri = undefined;
    wrapper.vm.postForm.title = undefined;
    wrapper.vm.postForm.content = undefined;

    expect(axios.post).toHaveBeenCalledTimes(3);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(4);
  });

});
