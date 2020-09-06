delete from chapter_phase_award_items;

delete from chapter_phase_award_cards;

delete from chapter_phase;

delete from chapter_details;

delete from chapter_award_items;

delete from chapter_award_cards;

delete from chapter;

insert into chapter(chapter_id, phase_no, phase_type)
	select 1, 2, 1;
insert into chapter_phase(chapter_id, phase_id)
	select 1, 1;
insert into chapter_phase(chapter_id, phase_id)
	select 1, 2;
insert into chapter_details(chapter_id, phase_id, position_id, card_id)
	select 1, 1, 1, 1;
insert into chapter_details(chapter_id, phase_id, position_id, card_id)
	select 1, 1, 6, 2;
insert into chapter_details(chapter_id, phase_id, position_id, card_id)
	select 1, 1, 11, 2;
insert into chapter_details(chapter_id, phase_id, position_id, card_id)
	select 1, 1, 16, 2;
insert into chapter_details(chapter_id, phase_id, position_id, card_id)
	select 1, 1, 21, 1;
insert into chapter_details(chapter_id, phase_id, position_id, card_id)
	select 1, 2, 12, 2;