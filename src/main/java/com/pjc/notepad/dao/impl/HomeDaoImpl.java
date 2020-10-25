package com.pjc.notepad.dao.impl;

import java.util.List;

import com.pjc.notepad.dao.HomeDao;
import com.pjc.notepad.vo.impl.MemberDto;
import com.pjc.notepad.vo.impl.NoteDto;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("HomeDao")
public class HomeDaoImpl implements HomeDao {

    private static Logger logger = LoggerFactory.getLogger(HomeDaoImpl.class);

    @Autowired
    SqlSession session;

    @Override
    public MemberDto loginPro(MemberDto dto) {
        try {
            dto = session.selectOne("loginPro", dto);
        } catch (Exception e) {
            logger.error("loginPro Exception : " + e.getMessage());
        }
        return dto;
    }

    @Override
    public List<NoteDto> noteList(MemberDto memberDto) {
        List<NoteDto> list = null;
        try {
            list = session.selectList("noteList", memberDto);
        } catch (Exception e) {
            logger.error("noteList Exception : " + e.getMessage());
        }
        return list;
    }

    @Override
    public int insertNote(NoteDto dto) {
        int result = 0;
        try {
            result = session.insert("insertNote", dto);
        } catch (Exception e) {
            logger.error("insertNote Exception : " + e.getMessage());
        }
        return result;
    }

}
