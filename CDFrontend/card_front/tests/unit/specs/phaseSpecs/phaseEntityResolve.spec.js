jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve()),
  post: jest.fn(() => Promise.resolve())
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import PhaseEditPanel from '@/components/table/phaseEditPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('PhaseEditPanel.vue', () => {
  const wrapper = shallowMount(PhaseEditPanel,
    {
      localVue
    });

  it('Phase Edit Panel Resolves handleCardChoose', () => {
    wrapper.vm.currCard = 2;
    let val = {
      cardId: 1
    };
    wrapper.vm.handleCardChoose(val);
    expect(wrapper.vm.currCard).toBe(1);
  });

  wrapper.vm.currChapter = 2;
  wrapper.vm.currPhase = 2;

  wrapper.vm.chapterData = [
    {
      phaseId: 1,
      positionId: 1,
      cardId: 1
    },
    {
      phaseId: 2,
      positionId: 2,
      cardId: 2
    },
    {
      phaseId: 3,
      positionId: 3,
      cardId: 3
    }
  ];

  it('Phase Edit Panel Resolves placeCard', () => {
    wrapper.vm.confirmed = true;
    wrapper.vm.placedCard = false;
    wrapper.vm.currChapter = undefined;
    wrapper.vm.posList = [1];
    wrapper.vm.posMap = new Map();
    wrapper.vm.posMap.set(1, 1);
    wrapper.vm.placeCard(1);
    expect(wrapper.vm.confirmed).toBeTruthy();
    expect(wrapper.vm.placedCard).toBeFalsy();
    expect(wrapper.vm.posList).toStrictEqual([1]);
    expect(wrapper.vm.posMap.size).toBe(1);

    wrapper.vm.currChapter = 1;
    wrapper.vm.currPhase = 1;
    wrapper.vm.currCard = 1;
    wrapper.vm.placeCard(1);
    expect(wrapper.vm.confirmed).toBeFalsy();
    expect(wrapper.vm.placedCard).toBeTruthy();
    expect(wrapper.vm.posList).toStrictEqual([]);
    expect(wrapper.vm.posMap.size).toBe(0);

    wrapper.vm.placeCard(1);
    expect(wrapper.vm.posList).toStrictEqual([1]);
    expect(wrapper.vm.posMap.size).toBe(1);
  });

  // it('Phase Edit Panel Resolves handleChange', () => {
  //   wrapper.vm.placedCard = false;
  //   wrapper.vm.handleChange();
  //
  //   wrapper.vm.placedCard = true;
  //   wrapper.vm.confirmed = true;
  //   wrapper.vm.handleChange();
  //   expect(wrapper.vm.confirmed).toBeTruthy();
  //
  //   wrapper.vm.placeCard(2);
  //   wrapper.vm.handleChangePhase();
  // });

});
