NCI-PID-PubMed Genomics Knowledge Base Completion Dataset

This dataset includes a database of regulation relationships among genes and corresponding textual mentions of pairs of genes in PubMed [4] article abstracts.
The database is derived from the NCI PID Pathway Interaction Database [3], and the textual mentions are extracted from cooccurring pairs of genes in PubMed abstracts, processed and annotated by Literome [1]. This dataset was used in the paper "Compositional Learning of Embeddings for Relation Paths in Knowledge Bases and Text" [2]. 



FILE FORMAT DETAILS

The files train.txt, valid.txt, and test.text contain the training, development, and test set knowledge base (database of regulation relationships) triples used in [2].
The file text.txt contains the textual triples derived from PubMed[4] via entity linking and processing with Literome [1]. The textual mentions were used for knowledge base completion in [2].

The knowledge base triples files contain lines like this:

ABL1    Positive_regulation     CDK2

The format is:

GENE1	relation	GENE2

The separator is a tab character; the relations are Positive_regulation, Negative_regulation, and Family (Family relationships occur only in the training set).

The textual mention file text.txt has lines like this:

GH1     [XXX]:<-nn>:X:<-dobj>:induce:<-vmod>:X  INS     1

This indicates the ids of the two genes, together with a partially lexicalized dependency path between the entities. The last element in the tuple is the number of occurrences of the specified entity pair with the given dependency path in sentences from PubMed abstracts.
The dependency paths are specified as sequences of words (like the word "induce" above) and labeled dependency links (like <dobj> above). The direction of traversal of a dependency arc is indicated by whether there is a - sign in front of the arc label "e.g." <-dobj> vs <dobj>. In some dependency paths, only "trigger" words are lexicalized and other words are marked with X. Please refer to the paper [2] for more details.


REFERENCES

[1] Hoifung Poon, Chris Quirk, Charlie DeZiel, and David Heckerman. Literome: PubMed-Scale Genomic Knowledge Base in the Cloud. In Bioinformatics 30.19 (2014), 2840-2842.
[2] Kristina Toutanova, Xi Victoria Lin, Scott Wen-tau Yih, Hoifung Poon, and Chris Quirk. Compositional Learning of Embeddings for Relation Paths in Knowledge Bases and Text. In Proceedings of ACL 2016.
[3] https://github.com/NCIP/pathway-interaction-database/tree/master/download
[4] https://www.ncbi.nlm.nih.gov/pubmed/


CONTACT

Please contact Kristina Toutanova (kristout@microsoft.com) or Hoifung Poon (hoifung@microsoft.com) if you have questions about the dataset.

