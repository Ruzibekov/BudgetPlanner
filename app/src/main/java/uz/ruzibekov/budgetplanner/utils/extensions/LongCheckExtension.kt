package uz.ruzibekov.budgetplanner.utils.extensions

fun Long.toPositive(): Boolean{
    return  this >= 0
}