package io.anaxo.alexa.napster.lambda

import com.amazon.ask.Skill
import com.amazon.ask.SkillStreamHandler
import javax.inject.Inject

class NapsterStreamHandler(@Inject val skill : Skill) : SkillStreamHandler(skill)
