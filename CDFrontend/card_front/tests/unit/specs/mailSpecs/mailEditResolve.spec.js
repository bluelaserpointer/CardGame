jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve()),
  post: jest.fn(() => Promise.resolve({data: true}))
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import MailEditPanel from '@/components/edit/MailEditPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('MailEditPanel.vue', () => {
  const wrapper = mount(MailEditPanel, { localVue });

  it('Mail Edit Panel Resolves created', () => {
    expect(wrapper.vm.postForm.image_uri).toBe('');
    expect(wrapper.vm.postForm.title).toBe('');
    expect(wrapper.vm.postForm.content).toBe('');
  });

  it('Mail Edit Panel Resolves submitForm', async () => {
    expect(axios.post).toHaveBeenCalledTimes(0);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  it('Mail Edit Panel Resolves submitForm branchLast', async () => {
    wrapper.vm.postForm.image_uri = undefined;
    wrapper.vm.postForm.title = undefined;
    wrapper.vm.postForm.content = undefined;

    expect(axios.post).toHaveBeenCalledTimes(1);
    await wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  // it('Mail Edit Panel Resolves resetArticle', () => {
  //   wrapper.vm.postForm.image_uri = 'xxx';
  //   wrapper.vm.postForm.title = 'xxx';
  //   wrapper.vm.postForm.content = 'xxx';
  //
  //   wrapper.vm.resetArticle();
  //
  //   expect(wrapper.vm.postForm.image_uri).toBe('');
  //   expect(wrapper.vm.postForm.title).toBe('');
  //   expect(wrapper.vm.postForm.content).toBe('');
  // });

});
