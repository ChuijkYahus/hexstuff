package vg.skye.hexstuff.casting.patterns.regex

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.asActionResult
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.iota.ListIota
import at.petrak.hexcasting.api.spell.iota.NullIota
import ram.talia.moreiotas.api.getString
import ram.talia.moreiotas.api.spell.iota.StringIota
import vg.skye.hexstuff.casting.getRegex

class OpMatchRegex : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val haystack = args.getString(0, argc)
        val pat = args.getRegex(1, argc)
        val matcher = pat.matcher(haystack)
        val matches = mutableListOf<Iota>()
        while (matcher.find()) {
            matches.add(ListIota((0..matcher.groupCount()).map {
                val group: String? = matcher.group(it)
                if (group == null)
                    NullIota()
                else
                    StringIota(group)
            }))
        }
        return matches.asActionResult
    }
}