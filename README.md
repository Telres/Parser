Ex


S0 -> closure( S -> E$)

S -> E$		(reduce 0)
E -> E*B	(reduce 1)
E -> E+B	(reduce 2)
E -> B		(reduce 3)
B -> 0		(reduce 4)


S0 (
S -> .E$
E -> .E*B
E -> .E+B
E -> .B
B -> .0
)

S1 = goto(S0, E) = closure {
S -> E.$ 
E -> E.*B
E -> E.+B
}

S2 = goto(S0, B) (
E -> B.
)

S3 = goto(S0, 0) (
B -> 0.
)

S4 = goto(S1, *) (
E -> E*.B
B -> .0
)

S5 = goto(S1, +) (
E -> E+.B
B -> .0
)

S6 = goto(S4, B) (
E -> E*B. 
)

S7 = goto(S5, B) (
E -> E+B.
)

S8 = goto(S4, 0) (
B -> 0.
)

S9 = goto(S5, 0) (
B -> 0.
)
